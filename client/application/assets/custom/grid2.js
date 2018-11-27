$(function() {
  $.ajax({
    type: "GET",
    url: "kategori/getAllkategori"
  }).done(function(data) {
   

    $("#jsGrid2").jsGrid({
      height: "300px",
      width: "100%",
      filtering: true,
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
            url: "kategori/getAllkategori",
            data: filter
          });
        },
        insertItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "obat/addobat/",
            data: item
          });
        },
        updateItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "obat/editobat/",
            data: item
          });
        },
        deleteItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "obat/deleteobat/",
            data: item
          });
        }
      },
      fields: [
      {
          name: "id_kategori",
          title: "id kategori",
          type: "text",
          width: 150
        },
        {
          name: "nama_kategori",
          title: "Nama Kategori",
          type: "text",
          width: 150
        },
        { type: "control",deleteButton: false,editButton : false,insertButton : false }],
        controller: {
                       loadData:  function(filter) {
                          return $.grep(data, function(item) {
                                     // client-side filtering below (be sure conditions are correct)
                             return(!filter.id_kategori|| item.id_kategori.indexOf(filter.id_kategori) > -1) 
                         && (!filter.nama_kategori|| item.nama_kategori.indexOf(filter.nama_kategori) > -1) 
                });
                  },
               }
    });
  });
});
