<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Mhs_model extends CI_Model
{

	public function login($username,$password)
	{
		$this->db->select('nim');
		$this->db->from('tb_mahasiswa');
		$this->db->where('nim', $username);
		$this->db->where('password', MD5($password));
		$q = $this->db->get();
		if($q->num_rows()==1){
			return $q->result_array();
		}else{
			return false;
		}
    }
}
