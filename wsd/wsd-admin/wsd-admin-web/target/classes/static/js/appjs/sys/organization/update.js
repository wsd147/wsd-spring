var Organ_Update ={
    prefix:"/sys/organization"
};

Organ_Update.save =function(){
    var ajx = new $ax(Organ_Update.prefix+"/save",function (data) {
        if(data.code == 200){
            parent.Alert.ok(data.message);
            var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);
            parent.Organ.loadTable();
        }else {
            parent.Alert.error(data.message);
        }
    },function () {
        parent.Alert.error("网络错误")
    });
    ajx.setData($('#signupForm').serialize());
    ajx.start();
}