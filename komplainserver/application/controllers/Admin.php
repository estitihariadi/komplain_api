<?php
defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/Format.php';

class Admin extends REST_Controller {

    function index_get(){
        $get_admin = $this->db->query("
            SELECT *
            FROM tb_admin order by id_admin asc")->result();
        $this->response(
         array(
             "status" => "success",
             "result" => $get_admin
         )
     );
    }

    function index_post() {
        $action  = $this->post('action');
        $data_admin = array(
            'id_admin' => $this->post('id_admin'),
            'nama_admin' => $this->post('nama_admin'),
            'username_admin' => $this->post('username_admin'),
            'password_admin' => $this->post('password_admin')
        );

        switch ($action) {
            case 'insert':
            $this->insertAdmin($data_admin);
            break;
            
            case 'update':
            $this->updateAdmin($data_admin);
            break;
            
            case 'delete':
            $this->deleteAdmin($data_admin);
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

    function insertAdmin($data_admin){
        // Cek validasi
        if (empty($data_admin['nama_admin']) || empty($data_admin['username_admin']) || empty($data_admin['password_admin'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "Nama / Username / Password Admin harus diisi"
                )
            );
        } else {
            $data_admin['password_admin'] = md5($data_admin['password_admin']);
            $do_insert = $this->db->insert('tb_admin', $data_admin);
            if ($do_insert){
                $this->response(
                    array(
                        "status" => "success",
                        "result" => array($data_admin),
                        "message" => $do_insert
                    )
                );
            }
        }
    }

    function updateAdmin($data_admin){
        // Cek validasi
        if (empty($data_admin['id_admin']) || empty($data_admin['nama_admin']) || empty($data_admin['username_admin']) || empty($data_admin['password_admin'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "ID / Nama / Username / Password Admin harus diisi"
                )
            );
        } else {
            // Cek apakah ada di database
            $get_admin_baseID = $this->db->query("
                SELECT 1 id_admin
                FROM tb_admin
                WHERE id_admin = {$data_admin['id_admin']}")->num_rows();

            if($get_admin_baseID === 0){
                // Jika tidak ada
                $this->response(
                    array(
                        "status"  => "failed",
                        "message" => "ID Admin tidak ditemukan"
                    )
                );
            } else {
                $data_admin['password_admin'] = md5($data_admin['password_admin']);
                $update = $this->db->query("
                    UPDATE tb_admin
                    SET
                    nama_admin        = '{$data_admin['nama_admin']}',
                    username_admin    = '{$data_admin['username_admin']}',
                    password_admin    = '{$data_admin['password_admin']}'
                    WHERE id_admin = {$data_admin['id_admin']}"
                );                
                if ($update){
                    $this->response(
                        array(
                            "status"    => "success",
                            "result"    => array($data_admin),
                            "message"   => $update
                        )
                    );
                }
            }   
        }
    }

    function deleteAdmin($data_admin){
        if (empty($data_admin['id_admin'])){
            $this->response(
                array(
                    "status" => "failed",
                    "message" => "ID Admin harus diisi"
                )
            );
        } else {
            // Cek apakah ada di database
            $get_admin_baseID = $this->db->query("
                SELECT 1 id_admin
                FROM tb_admin
                WHERE id_admin =  {$data_admin['id_admin']}")->num_rows();
            if($get_admin_baseID > 0){
                $this->db->query("
                    DELETE FROM tb_admin
                    WHERE id_admin = {$data_admin['id_admin']}");
                $this->response(
                    array(
                        "status" => "success",
                        "message" => "Data ID = " .$data_admin['id_admin']. " berhasil dihapus"
                    )
                );
            } else {
                $this->response(
                    array(
                        "status" => "failed",
                        "message" => "ID Admin tidak ditemukan"
                    )
                );
            }
        }
    }
}
