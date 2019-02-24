var Permission = {
    permissionTableId:"permissionTable",
    prefix:"/sys/permission"
}

//初始化表格列
Permission.initColunm = function(){
    var columns =  [
        {
            title: '编号',
            field: 'permissionId',
            visible: false,
            align: 'center',
            valign: 'center',
            width: '5%'
        },
        {
            title: '名称',
            valign: 'center',
            align:  'center',
            field: 'name',
            width: '10%'
        },

        {
            title: '图标',
            field: 'icon',
            align: 'center',
            valign: 'center',
            width : '5%',
            formatter: function (item, index) {
                return item.icon == null ? ''
                    : '<i class="' + item.icon
                    + ' fa-lg"></i>';
            }
        },
        {
            title: '类型',
            field: 'type',
            align: 'center',
            valign: 'center',
            width : '10%',
            formatter: function (item, index) {
                if (item.type === 0) {
                    return '<span class="label label-primary">目录</span>';
                }
                if (item.type === 1) {
                    return '<span class="label label-success">菜单</span>';
                }
                if (item.type === 2) {
                    return '<span class="label label-warning">按钮</span>';
                }
            }
        },
        {
            title: '地址',
            valign: 'center',
            width : '20%',
            field: 'uri'
        },
        {
            title: '权限标识',
            valign: 'center',
            width : '10%',
            field: 'permissionValue'
        },
        {
            title: '所属系统',
            valign: 'center',
            width : '10%',
            field: 'systemId'
        },
        {
            title: '状态',
            valign: 'center',
            width : '5%',
            field: 'status',
            formatter : function(item, index) {
                if (item.status == '0') {
                    return '<span class="label label-danger">禁用</span>';
                } else if (item.status == '1') {
                    return '<span class="label label-primary">正常</span>';
                }
            }
        },
        {
            title: '操作',
            field: 'id',
            align: 'center',
            valign: 'center',
            formatter: function (item, index) {
                var e = '<a class="btn btn-primary btn-sm '
                   /* + s_edit_h*/
                    + '" href="#" mce_href="#" title="编辑" onclick="Permission.showEdit(\''
                    + item.permissionId
                    + '\')"><i class="fa fa-edit"></i></a> ';
                var next = '<a class="btn btn-primary btn-sm '
                    /*+ s_add_h*/
                    + '" href="#" mce_href="#" title="添加同级" onclick="Permission.showAdd(\''
                    + item.pid
                    + '\')"><i class="fa fa-plus"></i></a> ';
                var child = '<a class="btn btn-success btn-sm '
                    /*+ s_add_h*/
                    + '" href="#" mce_href="#" title="添加下级" onclick="Permission.showAdd(\''
                    + item.permissionId
                    + '\')"><i class="fa fa-plus"></i></a> ';
                var d = '<a class="btn btn-warning btn-sm '
                  /*  + s_remove_h*/
                    + '" href="#" title="删除"  mce_href="#" onclick="Permission.removeOne(\''
                    + item.permissionId
                    + '\')"><i class="fa fa-remove"></i></a> ';
                var enable = '<a class="btn btn-primary btn-sm " href="#" title="启用"  mce_href="#" onclick="Permission.enable(\''
                    + item.permissionId
                    + '\')"><i class="fa fa-unlock"></i></a> ';
                var disable = '<a class="btn btn-warning btn-sm " href="#" title="禁用"  mce_href="#" onclick="Permission.disable(\''
                    + item.permissionId
                    + '\')"><i class="fa fa-lock"></i></a> ';
                return e +next+child+(item.status==0?enable:disable)+ d ;
            }
        }];
    return columns;
}

Permission.loadTable = function(){
    var treeTable =  new TreeTable(Permission.permissionTableId,Permission.prefix+"/list","permissionId","permissionId","pid",Permission.initColunm());
    treeTable.init();
}

Permission.showAdd = function(pid){
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : Permission.prefix + '/add/' + pid
    });
}


Permission.showEdit = function(id){
    layer.open({
        type : 2,
        title : '修改',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : Permission.prefix + '/edit/' + id
    });
}

Permission.removeOne = function(id){
    alert(id)
    layer.confirm("确定删除该权限吗？", {icon: 3, title:'提示'}, function(index){
        //do something
        var ajx = new $ax(Permission.prefix+"/remove/"+id,function (data) {
            if(data.code == 200){
                Alert.ok(data.message);
                Permission.loadTable();
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
Permission.enable = function(id){
    layer.confirm("确定启用该权限吗？", {icon: 3, title:'提示'}, function(index){
        //do something
        var ajx = new $ax(Permission.prefix+"/enableOrDisable/enable/"+id,function (data) {
            if(data.code == 200){
                Alert.ok(data.message);
                Permission.loadTable();
            }else {
                Alert.error(data.message);
            }
        },function () {
            Alert.error("网络错误")
        });
        ajx.setType("put")
        ajx.start();
        layer.close(index);
    });
}
Permission.disable = function(id){
    layer.confirm("若该权限存在子组织将会一并禁用，确定禁用该权限吗？", {icon: 3, title:'提示'}, function(index){
        //do something
        var ajx = new $ax(Permission.prefix+"/enableOrDisable/disable/"+id,function (data) {
            if(data.code == 200){
                Alert.ok(data.message);
                Permission.loadTable();
            }else {
                Alert.error(data.message);
            }
        },function () {
            Alert.error("网络错误")
        });
        ajx.setType("put")
        ajx.start();
        layer.close(index);
    });
}


$(function () {
    Permission.loadTable();
})
