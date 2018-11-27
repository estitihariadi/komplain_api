
$(function() {
  $("#grid").jsGrid({
    width: "100%",
    height: "800px",
    filtering: false,
      inserting: true,
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
       url: "Admin/getAdmin",
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
      { name: "id", visible: false },
      { name: "nama", title: "Nama", type: "text", width: 100 },
      { name: "username", type:"text", title: "Username", width:30},
      { name: "alamat", type:"textarea", title:"Alamat",  width: 60 },
      { name: "email", type:"text", title: "Email"},
      { name: "notelp", type:"text", title: "Notelp"},
      { 
        name: "foto",
        width: 100,
        type: 'readonly',
        itemTemplate: function(value, item) {
          return "<img src='"+base_url+"/assets/images/" + value + "' height='75' width='75'>"
        },
        },
      {name: "level", type:"readonly",title: "Level"},
      { type: "control" }
    ]
  });  
});