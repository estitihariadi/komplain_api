<?php
require APPPATH . '/libraries/REST_Controller.php';

class Pembelian2 extends REST_Controller{
    function __construct($config = 'rest')
    {
        parent::__construct($config);
    }

    function index_get(){
        $idpem=$this->get('id_pembelian');
        if($idpem==""){
            $this->db->select('pembelian.id_pembelian,pembeli.nama,pembelian.tanggal_beli,pembelian.total_harga,tiket.tujuan');
            $this->db->from('pembelian');
            $this->db->join('pembeli', 'pembelian.id_pembeli = pembeli.id_pembeli');
            $this->db->join('tiket', 'pembelian.id_tiket = tiket.id_tiket');
            $q = $this->db->get();
            $pembelian = $q->result();

    }else {
        $this->db->select('pembelian.id_pembelian,pembeli.nama,pembelian.tanggal_beli,pembelian.total_harga,tiket.tujuan');
            $this->db->from('pembelian');
            $this->db->join('pembeli', 'pembelian.id_pembeli = pembeli.id_pembeli');
            $this->db->join('tiket', 'pembelian.id_tiket = tiket.id_tiket');
        $this->db->where('id_pembelian',$idpem);
        $this->db->get('pembelian')->result();
    }
        $this->response($pembelian,200);
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

    public function cekKosongPost(){
        $idpem = $this->post('id_pembelian');
        $id_pembeli= $this->post('id_pembeli');
        $tanggal_beli= $this->post('tanggal_beli');
        $total_harga=$this->post('total_harga');
        $id_tiket=$this->post('id_tiket');
        if(empty($idpem)||empty($id_pembeli)||empty($tanggal_beli)||empty($total_harga)||empty($id_tiket)){
            return false;
        }
    }

    function index_post(){
        $data = array(
            'id_pembelian'=> $this->post('id_pembelian'),
            'id_pembeli'=>$this->post('id_pembeli'),
            'tanggal_beli'=> $this->post('tanggal_beli'),
            'total_harga'=>$this->post('total_harga'),
            'id_tiket'=>$this->post('id_tiket'),
        );
        $id = $this->post('id_pembelian');
        $idp = $this->post('id_pembeli');
        $idt = $this->post('id_tiket');
        if($this->cekKosongPost()==false){
            $this->response(array('Status'=>'Pastikan Tidak Ada yang kosong',502));
        }else if($this->cekidpem($id)==true){
            $this->response(array('Status'=>'Id pembelian sudah ada',502));
        }else if($this->cekidp($idp)==false){
            $this->response(array('Status'=>'Id pembeli Tidak Sama',502));
        }else if($this->cekidt($idt)==false) {
            $this->response(array('Status'=>'Id Tiket tidak sama',502));
        }else{
        $insert = $this->db->insert('pembelian',$data);
        if($insert){
            $this->response($data,200);
        }else{
            $this->response(array('status'=>'fail',502));
        }
    }
    }


    function index_put(){
        $idpem = $this->put('id_pembelian');
        $data = array(
            'id_pembelian'=> $this->put('id_pembelian'),
            'id_pembeli'=>$this->put('id_pembeli'),
            'tanggal_beli'=> $this->put('tanggal_beli'),
            'total_harga'=>$this->put('total_harga'),
            'id_tiket'=>$this->put('id_tiket'),
        );
        $this->db->where('id_pembelian',$idpem);
        $update = $this->db->update('pembelian',$data);

        $id = $this->put('id_pembelian');
        $idp = $this->put('id_pembeli');
        $idt = $this->put('id_tiket');
        if($this->cekidpem($id)==false){
            $this->response(array('Status'=>'Id pembelian Tidak Ada',502));
        }else if($this->cekidp($idp)==false){
            $this->response(array('Status'=>'Id pembeli Tidak Sama',502));
        }else if($this->cekidt($idt)==false) {
            $this->response(array('Status'=>'Id Tiket tidak sama',502));
        }else{
        if($update){
            $this->response(array($data,'status'=>'Edit Sukses',200));
        }else{
            $this->response(array('status'=>'fail',502));
        }
    }
    }

    function index_delete(){
        $idpem = $this->delete('id_pembelian');
        $this->db->where('id_pembelian',$idpem);
        $delete = $this->db->delete('pembelian');

        if($delete){
            $this->response(array('Sukses dengan id'=>$idpem,'status'=>'success',201));
        }else{
            $this->response(array('status'=>'ID TIDAK ADA',502));
    
}
    }


}