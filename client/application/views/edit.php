<head>
<link href="<?php echo base_url() ?>assets/css/bootstrap.min.css" rel="stylesheet">
<link href="<?php echo base_url() ?>assets/fonts/css/font-awesome.min.css" rel="stylesheet">
<link href="<?php echo base_url() ?>assets/css/animate.min.css" rel="stylesheet">
</head>
<body>
<?php echo form_open('mahasiswa/edit');?>
<?php echo form_hidden('nim',$mahasiswa[0]->nim);?>
 
<table class="table">
    <tr><td>NIM</td><td><?php echo form_input('',$mahasiswa[0]->nim,"disabled");?></td></tr>
    <tr><td>NAMA</td><td><?php echo form_input('nama',$mahasiswa[0]->nama);?></td></tr>
    <tr><td>JURUSAN</td><td>
            <select name="jurusan">
                <option>-- Pilih Jurusan --</option>
                <?php
                    $connection = mysqli_connect("localhost", "root", "") or die (mysql_error());
            
                    mysqli_select_db( $connection,"db_akademik") or die(mysql_error());
                    $sql = mysqli_query( $connection,'SELECT * FROM jurusan ORDER BY id_jurusan ASC;');
                ?>
                <?php if (mysqli_num_rows($sql) != 0) { ?>
                <?php while ($row = mysqli_fetch_array($sql)) { ?>
                <option><?php echo $row['id_jurusan']
                ?></option>
                <?php } ?>
                <?php } ?>
             </select>
        </td></tr>
    <tr><td>ALAMAT</td><td><?php echo form_input('alamat',$mahasiswa[0]->alamat);?></td></tr>
    <tr><td colspan="2">
        <?php echo form_submit('submit','Simpan');?>
        <?php echo anchor('mahasiswa','Kembali');?></td></tr>
</table>
<?php
echo form_close();
?>
</body>
