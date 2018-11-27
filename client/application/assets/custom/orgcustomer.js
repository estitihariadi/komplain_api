
$(function() {
  $("#grid").jsGrid({
    width: "100%",
    height: "400px",
    filtering: true,
      inserting: false,
      editing: false,
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
       url: "Customer/getCustomer",
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
            url: "Customer/deleteCustomer",
            data: item
          });
        },
      },
    fields: [
      { name: "id_customer", visible: false },
      { name: "nama_lengkap", title: "Nama", type: "text", width: 100 },
      { name: "tgl_lahir", type:"date", title: "Tanggal Lahir", width:30},
      { name: "username", type:"text", title:"Usename",  width: 50 },
      { name: "email", type:"text", title: "Email"},
      { name: "jenis_kelamin", type:"radio", title: "Jenis Kelamin",width: 30 },
      { name: "alamat", type:"textarea", title: "alamat"},
      { name: "notelp", type:"text", title: "No Telp"},
      { 
        name: "foto",
        width: 100,
        type: 'readonly',
        itemTemplate: function(value, item) {
          return "<img src='"+base_url+"/assets/images/" + value + "' height='75' width='75'>"
        },
        },
      { type: "control" }
    ]
  });  
});