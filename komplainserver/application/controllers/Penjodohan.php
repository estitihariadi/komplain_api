<?
require APPPATH . '/libraries/REST_Controller.php';

class Penjodohan extends REST_Controller {
    function __construct($config = 'rest')
    {
        parent::__construct($config);
    }

   // show data pembelian
   function index_get() {
    $this->db->select('j.id_jodoh, u.nama_user as "Nama Cowok", u2.nama_user as "Nama Cewek", s.nama_status, j.tanggal');
    $this->db->from('jodoh as j');
    $this->db->join('user as u', 'j.id_user_L = u.id_user');
    $this->db->join('user as u2', 'j.id_user_P = u2.id_user');
    $this->db->join('status_jodoh as s', 'j.id_status_jodoh = s.id_status_jodoh');
    $q = $this->db->get();
    $penjodohan = $q->result();
     
       $this->response(array("status"=>"success","result" => $penjodohan));
   }

   // insert pembelian
   function index_post() {
       $data_pembelian = array(
           'id_jodoh'   => $this->post('id_jodoh'),
           'id_user_L'     => $this->post('id_user_L'),
           'id_user_P'   => $this->post('id_user_P'),
           'id_status_jodoh'    => $this->post('id_status_jodoh'),
           'tanggal'       => $this->post('tanggal')
           );
      
       if  (empty($data_pembelian['id_jodoh'])){
            $this->response(array('status'=>'fail',"message"=>"id_jodoh kosong"));
       }
       else {
           $getId = $this->db->query("Select id_jodoh from jodoh where id_jodoh='".$data_pembelian['id_jodoh']."'")->result();
          
           //jika id_pembelian tidak ada dalam database maka eksekusi insert
           if (empty($getId)){
                    if (empty($data_pembelian['id_user_L'])){
                       $this->response(array('status'=>'fail',"message"=>"id_user_L kosong"));
                    }
                    else if(empty($data_pembelian['id_user_P'])){
                       $this->response(array('status'=>'fail',"message"=>"id_user_P kosong"));
                    }else if(empty($data_pembelian['id_status_jodoh'])){
                       $this->response(array('status'=>'fail',"message"=>"id_status_jodoh kosong"));
                    }else if(empty($data_pembelian['tanggal'])){
                       $this->response(array('status'=>'fail',"message"=>"tanggal kosong"));
                    }
                    else{
                       //jika masuk pada else atau kondisi ini maka dipastikan seluruh input telah di set
                       //jika akan melakukan pembelian id_pembeli dan id_tiket harus dipastikan ada
                       $getuserL = $this->db->query("Select id_user from user Where id_user='".$data_pembelian['id_user_L']."'")->result();
                       $getuserP = $this->db->query("Select id_user from user Where id_user='".$data_pembelian['id_user_P']."'")->result();
                       $getstatus = $this->db->query("Select id_status_jodoh from status_jodoh Where id_status_jodoh='".$data_pembelian['id_status_jodoh']."'")->result();
                       $message="";
                       if (empty($getuserL)||empty($getuserP)) $message.="id_user tidak ada/salah ";
                       if (empty($getstatus)) {
                           if (empty($message)) {
                               $message.="id_status tidak ada/salah";
                           }
                           else {
                               $message.="dan id_status tidak ada/salah";
                           }
                       }
                       if (empty($message)){
                           $insert= $this->db->insert('jodoh',$data_pembelian);
                           if ($insert){
                               $this->response(array('status'=>'success','result' => $data_pembelian,"message"=>$insert));   
                           }
                          
                       }else{
                           $this->response(array('status'=>'fail',"message"=>$message));   
                       }
                      
                    }
           }else{
               $this->response(array('status'=>'fail',"message"=>"id_jodoh sudah ada"));
           }  
       }
   }

   // update data pembelian
   function index_put() {
       $data_pembelian = array(
        'id_jodoh'   => $this->put('id_jodoh'),
        'id_user_L'     => $this->put('id_user_L'),
        'id_user_P'   => $this->put('id_user_P'),
        'id_status_jodoh'  => $this->put('id_status_jodoh'),
        'tanggal'       => $this->put('tanggal')
                   );
                   if  (empty($data_pembelian['id_jodoh'])){
                    $this->response(array('status'=>'fail',"message"=>"id_jodoh kosong"));
               }
               else {
                $getId = $this->db->query("Select id_jodoh from jodoh where id_jodoh='".$data_pembelian['id_jodoh']."'")->result();
                //jika id_pembelian harus ada dalam database
                if (empty($getId)){
                  $this->response(array('status'=>'fail',"message"=>"id_jodoh tidak ada/salah")); 
                }else{
                    //jika masuk disini maka dipastikan id_pembelian ada dalam database
                     if (empty($data_pembelian['id_user_L'])){
                        $this->response(array('status'=>'fail',"message"=>"id_user_L kosong"));
                     }
                     else if(empty($data_pembelian['id_user_P'])){
                        $this->response(array('status'=>'fail',"message"=>"id_user_P kosong"));
                     }else if(empty($data_pembelian['id_status_jodoh'])){
                        $this->response(array('status'=>'fail',"message"=>"id_status_jodoh kosong"));
                     }else if(empty($data_pembelian['tanggal'])){
                            $this->response(array('status'=>'fail',"message"=>"tanggal kosong"));
                     } 
                            else{
                               //jika masuk pada else atau kondisi ini maka dipastikan seluruh input telah di set
                               //jika akan melakukan pembelian id_pembeli dan id_tiket harus dipastikan ada
                               $getuserL = $this->db->query("Select id_user from user Where id_user='".$data_pembelian['id_user_L']."'")->result();
                               $getuserP = $this->db->query("Select id_user from user Where id_user='".$data_pembelian['id_user_P']."'")->result();
                               $getstatus = $this->db->query("Select id_status_jodoh from status_jodoh Where id_status_jodoh='".$data_pembelian['id_status_jodoh']."'")->result();
                               $message="";
                               if (empty($getuserL)||empty($getuserP)) $message.="id_user tidak ada/salah ";
                               if (empty($getstatus)) {
                                   if (empty($message)) {
                                       $message.="id_status tidak ada/salah";
                                   }
                                   else {
                                       $message.="dan id_status tidak ada/salah";
                                   }
                               }
                   if (empty($message)){
                       $this->db->where('id_jodoh',$data_pembelian['id_jodoh']);
                       $update= $this->db->update('jodoh',$data_pembelian);
                       if ($update){
                           $this->response(array('status'=>'success','result' => $data_pembelian,"message"=>$update));
                       }
                      
                   }else{
                       $this->response(array('status'=>'fail',"message"=>$message));   
                   }
                }
           }

       }
   }

   // delete pembelian
   function index_delete() {
       $id_pembelian = $this->delete('id_jodoh');
       if (empty($id_pembelian)){
           $this->response(array('status' => 'fail', "message"=>"id_jodoh harus diisi"));
       } else {
           $this->db->where('id_jodoh', $id_pembelian);
           $delete = $this->db->delete('jodoh');  
           if ($this->db->affected_rows()) {
               $this->response(array('status' => 'success','message' =>"Berhasil delete dengan id_jodoh = ".$id_pembelian));
           } else {
               $this->response(array('status' => 'fail', 'message' =>"id_jodoh tidak dalam database"));
           }
       }
   }
}  