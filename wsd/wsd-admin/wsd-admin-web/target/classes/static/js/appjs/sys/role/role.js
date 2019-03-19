var Role = {
	roleTableId:'roleTable',
	prefix:"/sys/role",
    tableobjct: null,
    table:null,
    authTree:null,
    width:null
}

//初始化表格列
Role.initColumn = function(){
    var columns = [[
        {field: 'selectItem', checkbox: true},
        {title: '角色名称', field: 'name', align: 'center'},
        {title: '角色标题', field: 'title', align: 'center'},
        {title: '角色描述', field: 'description', align: 'center'},
        {title: '状态', field: 'status', align: 'center',templet:function(item){
            var s = null;
            if(item.status==0){
                s = "<span class='layui-badge layui-bg-red'>未授权</span>";
            }else if(item.status==1){
                s = "<span class='layui-badge layui-bg-green'>已授权</span>"

            }
            return s;
        }},
        {title: '创建时间', field: 'ctime', align: 'center'},
        {title: '修改时间', field: 'utime', align: 'center'},
        {title: '操作', field: 'roleId', align: 'center', templet:function(item){
            var a = '<a class="btn btn-primary btn-sm '
                /* + s_edit_h*/
                + '" href="#" mce_href="#" title="授权" onclick="Role.showAuth(\''
                + item.roleId
                + '\')"><i class="fa fa-sitemap"></i></a> ';
            var e = '<a class="btn btn-primary btn-sm '
                /* + s_edit_h*/
                + '" href="#" mce_href="#" title="编辑" onclick="Role.showEdit(\''
                + item.roleId
                + '\')"><i class="fa fa-edit"></i></a> ';
            var d = '<a class="btn btn-warning btn-sm '
                /*  + s_remove_h*/
                + '" href="#" title="删除"  mce_href="#" onclick="Role.removeOne(\''
                + item.roleId
                + '\')"><i class="fa fa-remove"></i></a> ';
            return a+e + d ;
        }}
    ]];
    return columns;
};

//加载表格
Role.loadTable =function(){
    layui.use(['table'],function(){
        Role.tableobjct = layui.table;
        var defaultColunms = Role.initColumn();
        var table = new LAYtable(Role.roleTableId, Role.prefix+"/list/", defaultColunms);
        table.setTitle("角色列表");
        Role.table = table.init(Role.tableobjct);
    });
}

Role.reload = function(){
    var queryData = {};
    Role.table.refresh(queryData);
}

Role.showAuth =function(id){
    Role.loadTree(id);
    layer.open({
        type : 1,
        title : '授权',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ Role.width, '520px' ],
        content : $("#authTree"),
        btn: ['保存', '关闭'],
        yes: function(index, layero){
            Role.saveAuth(id);
            Role.loadTable();
            layer.close(index);
        }
        ,btn2: function(index, layero){
            layer.close(index);
        }
    });
    $("#authTree").show()
}

Role.loadTree=function(id){
    var setting = {
        check: {
            enable: true,
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    this.authTree = new $ZTree("tree", Role.prefix+"/getPermiTree/"+id);
    this.authTree.setSettings(setting);
    this.authTree.init();
    this.authTree.expendAll();
}

Role.saveAuth=function (roleId) {
   var ids  = this.authTree.getCheckedIds();
    var ajx = new $ax(Role.prefix+"/saveAuth",function (data) {
        if(data.code == 200){
            Alert.ok(data.message);
            Role.loadTable();
        }else {
            Alert.error(data.message);
        }
    },function () {
        Alert.error("网络错误")
    });
    ajx.setType("post")
    ajx.set("roleId",roleId);
    ajx.set("ids",ids);
    ajx.start();
}

Role.showAdd=function(){
    layer.open({
        type : 2,
        title : '添加角色',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ Role.width, '500px' ],
        content : Role.prefix + '/add' // iframe的url
    });
}

Role.showEdit=function(id){
        layer.open({
            type : 2,
            title : '角色修改',
            maxmin : true,
            shadeClose : true, // 点击遮罩关闭层
            area : [ Role.width, '520px' ],
            content : prefix + '/edit/' + id // iframe的url
        });
}

Role.removeOne=function(id){
    layer.confirm("确定删除该角色吗？", {icon: 3, title:'提示'}, function(index){
        //do something
        var ajx = new $ax(Role.prefix+"/removeOne/"+id,function (data) {
            if(data.code == 200){
                Alert.ok(data.message);
                Role.loadTable();
            }else {
                Alert.error(data.message);
            }
        },function () {
            Alert.error("网络错误")
        });
        ajx.setType("delete")
        ajx.start();
        layer.close(index);
    });
}

Role.batchRemove = function(){
   var checkStatus = Role.tableobjct.checkStatus(Role.roleTableId);
   if(checkStatus.data.length==0){
       Alert.warn("至少选择一行数据");
   }else{
       layer.confirm("确定删除选中角色吗？", {icon: 3, title:'提示'}, function(index){
           var ids = [];
           $.each(checkStatus.data,function (index,item) {
               ids.push(item.roleId);
           })
           var ajx = new $ax(Role.prefix+"/removeBatch",function (data) {
               if(data.code == 200){
                   Alert.ok(data.message);
                   Role.loadTable();
               }else {
                   Alert.error(data.message);
               }
           },function () {
               Alert.error("网络错误");
           });
           ajx.set("ids",ids);
           ajx.setType("post");
           ajx.start();
           layer.close(index);
       });
   }
}
$(function() {
    Role.width = $('#'+Role.roleTableId).parent().width()+'px';
    Role.loadTable();
});
