$(function() {
  $.ajax({
    type: "GET",
    url: "http://localhost:81/Tubes_Proyek2/index.php/pembayaran/getAllPembayaran"
  }).done(function(data) {

    $("#jsGrid").jsGrid({
      height: "300px",
      width: "100%",
      filtering: true,
      inserting: true,
      editing: true,
      sorting: true,
      paging: true,
      autoload: true,
      pageSize: 10,
      pageButtonCount: 5,
      deleteConfirm: "Do you really want to delete client?",
      controller: {
        loadData: function(filter) {
          return $.ajax({
            type: "GET",
            url: "pembayaran/getAllPembayaran/",
            data: filter
          });
        },
        insertItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "pembayaran/addpembayaran/",
            data: item
          });
        },
        updateItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "pembayaran/editpembayaran/",
            data: item
          });
        },
        deleteItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "pembayaran/deletepembayaran/",
            data: item
          });
        }
      },
      fields: [
      {
          name: "id_pembayaran",
          title: "id pembayaran",
          type: "text",
          width: 150
        },
      {
          name: "id_order",
          title: "id order",
          type: "text",
          width: 150
        },
      {
          name: "nama_pengirim",
          title: "Nama Pengirim",
          type: "text",
          width: 150
        },
        {
          name: "norek_pengirim",
          title: "No rekening Pengirim",
          type: "number",
          width: 150
        },
        {
          name: "norek_penerima",
          title: "No rekening penerima",
          type: "number",
          width: 150
        },
        {
          name: "bank_pengirim",
          title: "Bank pengirim",
          type: "text",
          width: 150
        },
        {
          name: "nominal_kiriman",
          title: "Nominal dikirim",
          type: "text",
          width: 150
        },
        {
          name: "tgl_transfer",
          title: "Tanggal transfer",
          type: "number",
          width: 150
        },
        {
          name: "bukti_pengiriman",
          title: "Bukti pengiriman",
          type: "text",
          width: 150
        },
        {
          name: "status_pembayaran",
          title: "Status pembayaran",
          type: "text",
          width: 150
        },
        { type: "control" }
      ]
    });
  });
});
