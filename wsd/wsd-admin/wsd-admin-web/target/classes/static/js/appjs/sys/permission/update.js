var Permission_Update ={
    prefix:"/sys/permission"
};

Permission_Update.openIcon = function(){
        layer.open({
            type: 2,
            title:'图标列表',
            content: Permission_Update.prefix+'/showIcon',
            area: ['480px', '90%'],
            success: function(layero, index){
                //var body = layer.getChildFrame('.ico-list', index);
                //console.log(layero, index);
            }
        });
}

Permission_Update.save =function(){
    var ajx = new $ax(Permission_Update.prefix+"/save",function (data) {
        if(data.code == 200){
            parent.Alert.ok(data.message);
            var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);
            parent.Permission.loadTable();
        }else {
            parent.Alert.error(data.message);
        }
    },function () {
        parent.Alert.error("网络错误")
    });
    ajx.setData($('#signupForm').serialize());
    ajx.start();
}

Permission_Update.validateRule=function () {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            name : {
                required : true
            },
            type : {
                required : true
            }
        },
        messages : {
            name : {
                required : icon + "请输入菜单名"
            },
            type : {
                required : icon + "请选择菜单类型"
            }
        }
    })
}