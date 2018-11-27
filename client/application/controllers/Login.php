<?php
Class Login extends CI_Controller{
    
    var $API ="";
    
    function __construct() {
        parent::__construct();
        $this->API="http://127.0.0.1/komplain/mhsserver/index.php";
    }

    function index(){
        $this->load->view('login_view');
    }

    function cekLogin(){
        $data = array(
            'nim'       =>  $this->input->post('nim'),
            'password'      =>  $this->input->post('password'),
        );
        $user =  json_decode($this->curl->simple_post($this->API.'/Login/login', $data, array(CURLOPT_BUFFERSIZE => 10))); 
        if($user->status == "success"){

            redirect("gg");
        }else{
            redirect("gagal");
        }
        
       
    }

}