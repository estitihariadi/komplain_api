
<head>

  <link href="<?php echo base_url() ?>assets/css/bootstrap.min.css" rel="stylesheet">
<link href="<?php echo base_url() ?>assets/fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="<?php echo base_url() ?>assets/css/animate.min.css" rel="stylesheet">
</head>
<body>
<?php echo $this->session->flashdata('hasil'); ?>

<table class="table">
    <tr><th>Id Pembelian</th><th>NAMA</th><th>Tanggal Beli</th><th>Total Harga</th><th>Tujuan</th><th>EDIT</th><th>DELETE</th></tr>
    <?php
   
    foreach ($pembelian as $p){
        echo "<tr>
              <td>$p->id_pembelian</td>
              <td>$p->nama</td>
              <td>$p->tanggal_beli</td>
              <td>$p->total_harga</td>
              <td>$p->tujuan</td>
              <td>".anchor('pembelian/edit/'.$p->id_pembelian,'Edit')."</td>
              <td>".anchor('pembelian/delete/'.$p->id_pembelian,'Delete')."</td>
              </tr>
              <tr>";
     
    }
?>
</table>
<?php
echo"
<td>".anchor('pembelian/create/'.$p->id_pembelian,'Create')."</td>
</tr>";    
?>

</body>



