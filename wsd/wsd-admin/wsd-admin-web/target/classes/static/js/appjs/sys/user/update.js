var User_Update = {
    prefix :"/sys/user",
};

User_Update.loadOrginTree = function(elem,onclickFun){
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback : {
            onClick : onclickFun
        }
    };
    var origanTree = new $ZTree(elem, User_Update.prefix+"/getOrignTree");
    origanTree.setSettings(setting);
    origanTree.init();
}
User_Update.openOrgn = function(){
    var index = layer.open({
        type : 1,
        title : '组织选择',
        maxmin : true,
        shadeClose : false,
        area : [ '300px', '300px' ],
        content : $(".organTreeSelect")
    });
    User_Update.loadOrginTree("organTreeSelect",function (event, treeId, treeNode) {
        if(treeNode.children==undefined){
            $("#organizationId").val(treeNode.id);
            $("#organizaName").val(treeNode.name);
            layer.close(index)
        }
    })
    $(".organTreeSelect").show();
}

User_Update.save =function(){
    var ajx = new $ax(User_Update.prefix+"/save",function (data) {
        if(data.code == 200){
            parent.Alert.ok(data.message);
            var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);
            parent.User.loadTable();
        }else {
            parent.Alert.error(data.message);
        }
    },function () {
        parent.Alert.error("网络错误")
    });
    ajx.setData($('#signupForm').serialize());
    ajx.start();
}