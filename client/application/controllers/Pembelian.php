<?php
Class Pembelian extends CI_Controller{
    
    var $API ="";
    
    function __construct() {
        parent::__construct();
        $this->API="http://localhost/servertiket/index.php";
    }
    
    // menampilkan data mahasiswa
    function index(){
        
        $data['p'] = json_decode($this->curl->simple_get($this->API.'/pembelian'));
        
               $this->load->library('xml_writer');
        
        // Initiate class
        $xml = new Xml_writer();
        $xml->setRootName('TIKET_KERETA');
        $xml->initiate();
        
        // Start branch 'cars'
        $xml->startBranch('PEMBELIAN');
        foreach($data['p'] as $a){
        // Set children for branch 'cars'
        $xml->startBranch('id', array('IdPembelian' => $a->id_pembelian));
        $xml->addNode('Nama', $a->nama);
        $xml->addNode('Tanggal_beli', $a->tanggal_beli);
        $xml->addNode('Total_Harga', $a->total_harga);
        $xml->addNode('Tujuan', $a->tujuan);
        $xml->endBranch();
         }
        $xml->endBranch();
       
        $data = array();
        $data['xml'] = $xml->getXml(FALSE);
        $this->load->view('xml', $data);	
        
      
      
    }

    public function cekidpem($id){
        $this->db->select('id_pembelian');
		$this->db->from('pembelian');
		$this->db->where('id_pembelian =',$id);
		$query = $this->db->get();
	  if($query->num_rows()>0){
		  return true;
	  }
    }

    
    public function cekidp($id){
        $this->db->select('id_pembeli');
		$this->db->from('pembeli');
		$this->db->where('id_pembeli =',$id);
		$query = $this->db->get();
	  if($query->num_rows()>0){
		  return true;
	  }
    }

    public function cekidt($id){
        $this->db->select('id_tiket');
		$this->db->from('tiket');
		$this->db->where('id_tiket =',$id);
		$query = $this->db->get();
	  if($query->num_rows()>0){
		  return true;
	  }
    }

    
    
    // insert data mahasiswa
    function create(){
        if(isset($_POST['submit'])){
            $data = array(
            'id_pembelian'=> $this->input->post('id_pembelian'),
            'id_pembeli'=>$this->input->post('id_pembeli'),
            'tanggal_beli'=> $this->input->post('tanggal_beli'),
            'total_harga'=>$this->input->post('total_harga'),
            'id_tiket'=>$this->input->post('id_tiket'),
        );

       
      
            $insert =  $this->curl->simple_post($this->API.'/pembelian', $data, array(CURLOPT_BUFFERSIZE => 10)); 
            if($insert)
            {
                $this->session->set_flashdata('hasil','Insert Data Berhasil');
            }else
            {
               $this->session->set_flashdata('hasil','Insert Data Gagal');
            }
            redirect('pembelian');
        
        }else{
            $data['jurusan'] = json_decode($this->curl->simple_get($this->API.'/jurusan'));
            $this->load->view('create',$data);
        }
    }
    
    // edit data mahasiswa
    function edit(){
        if(isset($_POST['submit'])){
            $data = array(
                'nim'       =>  $this->input->post('nim'),
                'nama'      =>  $this->input->post('nama'),
                'id_jurusan'=>  $this->input->post('jurusan'),
                'alamat'    =>  $this->input->post('alamat'));
            $update =  $this->curl->simple_put($this->API.'/mahasiswa', $data, array(CURLOPT_BUFFERSIZE => 10)); 
            if($update)
            {
                $this->session->set_flashdata('hasil','Update Data Berhasil');
            }else
            {
               $this->session->set_flashdata('hasil','Update Data Gagal');
            }
            redirect('mahasiswa');
        }else{
            $data['jurusan'] = json_decode($this->curl->simple_get($this->API.'/jurusan'));
            $params = array('nim'=>  $this->uri->segment(4));
            $data['mahasiswa'] = json_decode($this->curl->simple_get($this->API.'/mahasiswa',$params));
            $this->load->view('edit',$data);
        }
    }
    
    // delete data mahasiswa
    function delete($nim){
        if(empty($nim)){
            redirect('mahasiswa');
        }else{
            $delete =  $this->curl->simple_delete($this->API.'/mahasiswa', array('nim'=>$nim), array(CURLOPT_BUFFERSIZE => 10)); 
            if($delete)
            {
                $this->session->set_flashdata('hasil','Delete Data Berhasil');
            }else
            {
               $this->session->set_flashdata('hasil','Delete Data Gagal');
            }
            redirect('mahasiswa');
        }
    }
}
