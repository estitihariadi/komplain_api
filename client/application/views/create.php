<head>
<link href="<?php echo base_url() ?>assets/css/bootstrap.min.css" rel="stylesheet">
<link href="<?php echo base_url() ?>assets/fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="<?php echo base_url() ?>assets/css/animate.min.css" rel="stylesheet">
</head>
<body>
<?php echo form_open('pembelian/create');?>
<table class="table">
<tr><th>ID Pembelian</th><td><?php echo form_input('id_pembelian');?></td></tr>
<tr><th>ID Pembeli</th><td><?php echo form_input('id_pembeli');?></td></tr>
<tr><th>Tanggal Beli</th><td><?php echo form_input('tanggal_beli');?></td></tr>
<tr><th>Total Harga</th><td><?php echo form_input('total_harga');?></td></tr>
<tr><th>ID Tiket</th><td><?php echo form_input('id_tiket');?></td></tr>
<tr><td colspan="2">
<?php echo form_submit('submit','Simpan');?>
<?php echo anchor('pembelian','Kembali');?></td></tr>
</table>
<?php
echo form_close();
?>
</body>
