<?php


defined('BASEPATH') OR exit('No direct script access allowed');

// Jika ada pesan "REST_Controller not found"
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/Format.php';

class Login extends REST_Controller {

    function login_post(){
    $isi = array(
        'nim' => $this->post('nim'),
        'password' => $this->post('password')
);
$this->form_validation->set_data($isi);
    $this->form_validation->set_rules('nim', 'nim', 'required');
    $this->form_validation->set_rules('password', 'password', 'required|callback_cekDB');
    if ($this->form_validation->run() == FALSE) {
        $this->response(
            array(
                "status" => "error",
            )
        );
    } else {
        $this->response(
            array(
                "status" => "success",
                
            )
        );
    }
}

public function cekDB($password)
{
    $this->load->Model('mhs_model');
    $username = $this->post('nim');
    $hasil = $this->mhs_model->login($username,$password);
    if($hasil){
        return true;
    }else{
        return false;
    }

}

}