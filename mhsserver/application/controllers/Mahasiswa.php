<?php


defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/Format.php';

class Mahasiswa extends REST_Controller {


    function all_get(){
        $get_mhs = $this->db->query("
            SELECT
                *
            FROM tb_mahasiswa order by nim asc")->result();
       $this->response(
           array(
               "status" => "success",
               "result" => $get_mhs
           )
       );
    }

}