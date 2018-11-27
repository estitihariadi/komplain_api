var data = jQuery.parseJSON(response);

$(function() {

  $("#grid").jsGrid({
    
    width: "100%",
    height: "400px",
    filtering: false,
      inserting: true,
      editing: true,
      sorting: true,
      paging: true,
      autoload: true,
      pageSize: 10,
      pageButtonCount: 5,
      deleteConfirm: "Do you really want to delete client?",
    controller: {
    loadData: function (filter) {
     var data = $.Deferred();
     return $.ajax({
       type: "GET",
       url: "Mahasiswa/hasil",
       dataType: "json"
       }).done(function(response){
         data.resolve(response);
     });
      return data.promise();
    },
    insertItem: function(item) {
      return $.ajax({
          type: "POST",
          url: "Admin/addAdmin",
          data: item,
        });
      },
        deleteItem: function(item) {
          return $.ajax({
            type: "POST",
            url: "Admin/deleteAdmin",
            data: item
          });
        },
      },
      
    fields: [
      { name: "data[0]", title: "NIM", type: "text", width: 100 },
      { name: "data[0]", title: "Nama", type: "text", width: 100 },
      { name: "data[0]", type:"textarea", title:"Alamat",  width: 60 },
      { type: "control" }
    ]
  });  
});