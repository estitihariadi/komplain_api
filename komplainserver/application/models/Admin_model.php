<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Admin_model extends CI_Model
{

	public function login($username,$password)
	{
		$this->db->select('username_admin');
		$this->db->from('tb_admin');
		$this->db->where('username_admin', $username);
		$this->db->where('password_admin', MD5($password));
		$q = $this->db->get();
		if($q->num_rows()==1){
			return $q->result_array();
		}else{
			return false;
		}
    }
}
