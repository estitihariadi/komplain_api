<?php


defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/Format.php';

class Kategori extends REST_Controller {

    // Konfigurasi letak folder untuk upload image

    function all_get(){
        $get_kategori = $this->db->query("
            SELECT
                id_kategori,
                nama_kategori
            FROM tb_kategori order by id_kategori asc")->result();
       $this->response(
           array(
               "status" => "success",
               "result" => $get_kategori
           )
       );
    }

    function all_post() {

        $action  = $this->post('action');
        $data_kategori = array(
                        'id_kategori' => $this->post('id_kategori'),
                        'nama_kategori' => $this->post('nama_kategori')
                    );

        switch ($action) {
            case 'insert':
                $this->insertKategori($data_kategori);
                break;
            
            case 'update':
                $this->updateKategori($data_kategori);
                break;
            
            case 'delete':
                $this->deleteKategori($data_kategori);
                break;
            
            default:
                $this->response(
                    array(
                        "status"  =>"failed",
                        "message" => "action harus diisi"
                    )
                );
                break;
        }
    }

    function insertKategori($data_kategori){

        // Cek validasi
        if (empty($data_kategori['nama_kategori'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "Nama Kategori harus diisi"
                )
            );
        } else {

            $do_insert = $this->db->insert('tb_kategori', $data_kategori);
           
            if ($do_insert){
                $this->response(
                    array(
                        "status" => "success",
                        "result" => array($data_kategori),
                        "message" => $do_insert
                    )
                );
            }
        }
    }

    function updateKategori($data_kategori){

        // Cek validasi
        if (empty($data_kategori['nama_kategori'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "Nama Kategori harus diisi"
                )
            );
        } else {
            // Cek apakah ada di database
            $get_kategori_baseID = $this->db->query("
                SELECT 1 id_kategori
                FROM tb_kategori
                WHERE id_kategori =  {$data_kategori['id_kategori']}")->num_rows();

            if($get_kategori_baseID === 0){
                // Jika tidak ada
                $this->response(
                    array(
                        "status"  => "failed",
                        "message" => "ID Kategori tidak ditemukan"
                    )
                );
            } else {
            
              
                    $update = $this->db->query("
                        UPDATE tb_kategori
                        SET
                        nama_kategori    = '{$data_kategori['nama_kategori']}'
                        WHERE id_kategori = {$data_kategori['id_kategori']}"
                    );
               
                if ($update){
                    $this->response(
                        array(
                            "status"    => "success",
                            "result"    => array($data_kategori),
                            "message"   => $update
                        )
                    );
                }
            }   
        }
    }

    function deleteKategori($data_kategori){

        if (empty($data_kategori['id_kategori'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "ID Pembeli harus diisi"
                )
            );
        } else {
            // Cek apakah ada di database
            $get_kategori_baseID = $this->db->query("
            SELECT 1 id_kategori
            FROM tb_kategori
            WHERE id_kategori =  {$data_kategori['id_kategori']}")->num_rows();


            if($get_kategori_baseID > 0){
                
              
                    $this->db->query("
                        DELETE FROM tb_kategori
                        WHERE id_kategori = {$data_kategori['id_kategori']}");
                    $this->response(
                        array(
                            "status" => "success",
                            "message" => "Data ID = " .$data_kategori['id_kategori']. " berhasil dihapus"
                        )
                    );
                
            
            } else {
                $this->response(
                    array(
                        "status" => "failed",
                        "message" => "ID Pembeli tidak ditemukan"
                    )
                );
            }
        }
    }


}
