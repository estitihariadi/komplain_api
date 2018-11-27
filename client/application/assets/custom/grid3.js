$(function() {
  $.ajax({
    type: "GET",
    url: "http://localhost:81/TugasAkhir/index.php/penverifikasian/getAllverif"
  }).done(function(countries) {
    countries.unshift({ id: "0", name: "" });

    $("#jsGrid3").jsGrid({
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
            url: "penverifikasian/getAllverif/",
            data: filter
          });
        },
        updateItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "penverifikasian/editverif/",
            data: item
          });
        },
        deleteItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "penverifikasian/deleteverif/",
            data: item
          });
        }
      },
      fields: [
      {
          name: "nama_pasien",
          title: "Nama Pasien",
          type: "text",
          width: 150
        },
        {
          name: "umur",
          title: "Umur",
          type: "text",
          width: 150
        },
        {
          name: "keluhan",
          title: "Keluhan",
          type: "text",
          width: 150
        }, 
        {
          name: "foto_luka",
          title: "Foto luka",
          type: "text",
          width: 150
        }, 
        {
          name: "ket_obat",
          title: "Obat",
          type: "text",
          width: 150
        }, 
        {
          name: "status",
          title: "Status",
          type: "text",
          width: 150
        }, 
        { type: "control" }
      ]
    });
  });
});
