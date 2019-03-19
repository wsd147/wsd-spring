var Role_Update = {
    prefix:"/sys/role"
}

Role_Update.save =function(){
    var ajx = new $ax(Role_Update.prefix+"/save",function (data) {
        if(data.code == 200){
            parent.Alert.ok(data.message);
            var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);
            parent.Role.loadTable();
        }else {
            parent.Alert.error(data.message);
        }
    },function () {
        parent.Alert.error("网络错误")
    });
    ajx.setData($('#signupForm').serialize());
    ajx.start();
}