
$(function() {
  $("#grid").jsGrid({
    width: "100%",
    height: "400px",
      paging: true,
      autoload: true,
    
    controller: {
    loadData: function (filter) {
     var data = $.Deferred();
     return $.ajax({
       type: "GET",
       url: "Admin/Admin/getOrganization",
       dataType: "json"
       }).done(function(response){
         data.resolve(response);
     });
      return data.promise();
    },

      },
    fields: [
      { name: "nama", title: "Nama", type: "text", width: 100 },
      { type: "control" }
    ]
  });  
});