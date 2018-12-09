<?php
defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/Format.php';

class Komplain extends REST_Controller {
    private $folder_upload = 'uploads/';
    function index_get(){
        $get_komplain = $this->db->query("
            SELECT *
            FROM tb_komplain order by id_komplain asc")->result();
        $this->response(
         array(
             "status" => "success",
             "result" => $get_komplain
         )
     );
    }

    function bynim_post(){
        $nim = $this->post('nim');
        $get_komplain = $this->db->query("
            SELECT *
            FROM tb_komplain where nim ={$nim} order by id_komplain asc")->result();
        $this->response(
         array(
             "status" => "success",
             "result" => $get_komplain
         )
     );
    }

  

    function index_post() {
        $action  = $this->post('action');
        $data_komplain = array(
            'id_komplain'   => $this->post('id_komplain'),
            'nim'           => $this->post('nim'),
            'id_pegawai'    => $this->post('id_pegawai'),
            'judul'         => $this->post('judul'),
            'id_kategori'   => $this->post('id_kategori'),
            'foto'          => $this->post('foto'),
            'foto_after'    => $this->post('foto_after'),
            'keluhan'       => $this->post('keluhan'),
            'lokasi'        => $this->post('lokasi'),
            'tgl_komplain'  => $this->post('tgl_komplain'),
            'status'        => $this->post('status')
        );

        switch($action) {
            case 'insert':
            $this->insertKomplain($data_komplain);
            break;
            case 'update':
            $this->updateKomplain($data_komplain);
            break;
            case 'delete':
            $this->deleteKomplain($data_komplain);
            break;
            default:
            $this->response(
                array(
                    "status"  =>"failed",
                    "message" => "action harus diisi / action tidak ada"
                )
            );
            break;
        }
    }

    function insertKomplain($data_komplain){
        // Cek validasi
        if (empty($data_komplain['nim']) || empty($data_komplain['judul']) || empty($data_komplain['keluhan']) || empty($data_komplain['lokasi']) || empty($data_komplain['id_kategori'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "NIM / Judul / Keluhan / Lokasi / ID Kategori / Foto untuk insert komplain harus diisi"
                )
            );
        } else {
            $data_komplain['id_pegawai'] = 1;
            $data_komplain['foto'] = $this->uploadFoto();
            $data_komplain['foto_after'] = "";
            $data_komplain['tgl_komplain'] = date("Y-m-d");
            $data_komplain['status'] = "Belum";

            $do_insert = $this->db->insert('tb_komplain', $data_komplain);
            if ($do_insert){
                $this->response(
                    array(
                        "status" => "success",
                        "result" => array($data_komplain),
                        "message" => $do_insert
                    )
                );
            }
        }
    }

    function updateKomplain($data_komplain){
        // Cek validasi
        if (empty($data_komplain['id_komplain']) || empty($data_komplain['status'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "Nama / Status Komplain harus diisi"
                )
            );
        } else {
            // Cek apakah ada di database
            $get_komplain_baseID = $this->db->query("
                SELECT 1 id_komplain
                FROM tb_komplain
                WHERE id_komplain = {$data_komplain['id_komplain']}")->num_rows();

            if($get_komplain_baseID === 0){
                // Jika tidak ada
                $this->response(
                    array(
                        "status"  => "failed",
                        "message" => "ID Komplain tidak ditemukan"
                    )
                );
            } else {
              
                $data_komplain['foto_after'] = $this->uploadPhoto();

                if ($data_komplain['foto_after']){
                    // Jika upload foto berhasil, eksekusi update
                    $update = $this->db->query("
                        UPDATE tb_komplain SET
                            status = '{$data_komplain['status']}',
                            id_pegawai = '{$data_komplain['id_pegawai']}',
                            foto_after = '{$data_komplain['foto_after']}'
                        WHERE id_komplain = '{$data_komplain['id_komplain']}'");

                } else {
                    // Jika foto kosong atau upload foto tidak berhasil, eksekusi update
                    $update = $this->db->query("
                        UPDATE tb_komplain SET
                            status = '{$data_komplain['status']}',
                            id_pegawai = '{$data_komplain['id_pegawai']}'
                        WHERE id_komplain = '{$data_komplain['id_komplain']}'");
                }
            
                if ($update){
                    $this->response(
                        array(
                            "status" => "success",
                            "result" => array($data_komplain),
                            "message" => $update
                        )
                    );
                }else{
                    $this->response(
                        array(
                            "status" => "failed",
                            "message" => "error bos"
                        )
                    );
                }
            }   
        }
    }

    function deleteKomplain($data_komplain){
        if (empty($data_komplain['id_komplain'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "ID Komplain harus diisi"
                )
            );
        } else {
            // Cek apakah ada di database
            $get_komplain_baseID = $this->db->query("
                SELECT 1 id_komplain
                FROM tb_komplain
                WHERE id_komplain =  {$data_komplain['id_komplain']}")->num_rows();
            if($get_komplain_baseID > 0){
                $this->db->query("
                    DELETE FROM tb_komplain
                    WHERE id_komplain = {$data_komplain['id_komplain']}");
                $this->response(
                    array(
                        "status" => "success",
                        "message" => "Data ID = " .$data_komplain['id_komplain']. " berhasil dihapus"
                    )
                );
            } else {
                $this->response(
                    array(
                        "status" => "failed",
                        "message" => "ID Komplain tidak ditemukan"
                    )
                );
            }
        }
    }


    function uploadPhoto() {

        // Apakah user upload gambar?
        if ( isset($_FILES['foto_after']) && $_FILES['foto_after']['size'] > 0 ){

            // Foto disimpan di android-api/uploads
            $config['upload_path'] = realpath(FCPATH . $this->folder_upload);
            $config['allowed_types'] = 'jpg|png';

            // Load library upload & helper
            $this->load->library('upload', $config);
            $this->load->helper('url');

            // Apakah file berhasil diupload?
            if ( $this->upload->do_upload('foto_after')) {

               // Berhasil, simpan nama file-nya
               // URL image yang disimpan adalah http://localhost/android-api/uploads/namafile
               $img_data = $this->upload->data();
               $post_image = $this->folder_upload .$img_data['file_name'];

            } else {

                // Upload gagal, beri nama image dengan errornya
                // Ini bodoh, tapi efektif
                $post_image = $this->upload->display_errors();
                
            }
        } else {
            // Tidak ada file yang di-upload, kosongkan nama image-nya
            $post_image = '';
        }

        return $post_image;
    }

    function uploadFoto() {

        // Apakah user upload gambar?
        if ( isset($_FILES['foto']) && $_FILES['foto']['size'] > 0 ){

            // Foto disimpan di android-api/uploads
            $config['upload_path'] = realpath(FCPATH . $this->folder_upload);
            $config['allowed_types'] = 'jpg|png';

            // Load library upload & helper
            $this->load->library('upload', $config);
            $this->load->helper('url');

            // Apakah file berhasil diupload?
            if ( $this->upload->do_upload('foto')) {

               // Berhasil, simpan nama file-nya
               // URL image yang disimpan adalah http://localhost/android-api/uploads/namafile
               $img_data = $this->upload->data();
               $post_image = $this->folder_upload .$img_data['file_name'];

            } else {

                // Upload gagal, beri nama image dengan errornya
                // Ini bodoh, tapi efektif
                $post_image = $this->upload->display_errors();
                
            }
        } else {
            // Tidak ada file yang di-upload, kosongkan nama image-nya
            $post_image = '';
        }

        return $post_image;
    }
}
