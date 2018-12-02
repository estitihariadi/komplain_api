<?php
defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/Format.php';

class Komplain extends REST_Controller {

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

    function index_post() {
        $action  = $this->post('action');
        $data_komplain = array(
            'id_komplain' => $this->post('id_komplain'),
            'status' => $this->post('status')
        );

        switch ($action) {
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
                $update = $this->db->query("
                    UPDATE tb_komplain
                    SET
                    nama_komplain    = '{$data_komplain['nama_komplain']}',
                    status           = '{$data_komplain['status']}'
                    WHERE id_komplain = {$data_komplain['id_komplain']}"
                );                
                if ($update){
                    $this->response(
                        array(
                            "status"    => "success",
                            "result"    => array($data_komplain),
                            "message"   => $update
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
}
