$(function() {
  $.ajax({
    type: "GET",
    url: "http://localhost:81/TugasAkhir/index.php/jadwal2/getAlljadwal"
  }).done(function(countries) {
    countries.unshift({ id: "0", name: "" });

    $("#jsGrid4").jsGrid({
      height: "300px",
      width: "100%",
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
            url: "jadwal2/getAlljadwal/",
            data: filter
          });
        },
        insertItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "jadwal2/addjadwal/",
            data: item
          });
        },
        updateItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "jadwal2/editjadwal/",
            data: item
          });
        },
        deleteItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "jadwal2/deletejadwal/",
            data: item
          });
        }
      },
      fields: [
      {
          name: "username",
          title: "Username",
          type: "text",
          width: 150
        },
      {
          name: "hari",
          title: "Hari Kerja",
          type: "text",
          width: 150
        },
        {
          name: "jam_kerja",
          title: "Jam Kerja",
          type: "text",
          width: 150
        },
        {
          name: "jam_kerja_ed",
          title: "Jam Kerja Berakhir",
          type: "text",
          width: 150
        },
        { type: "control" }
      ]
    });
  });
});
