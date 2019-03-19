var Organ ={
    organizationTableId:"organizationTableId",
    prefix:"/sys/organization"
}

Organ.initColumn= function(){
    var column = [
        {
            title : 'ID',
            field : 'id',
            visible : false,
            align : 'center',
            valign : 'center',
            width : '50px',
            checkbox : true
        },
        {
            title : '组织编码',
            field : 'organizationId',
            visible : false,
            align : 'center',
            valign : 'center',
            width : '50px',
            checkbox : true
        },
        {
            field : 'name',
            title : '组织名称',
            valign : 'center',
            witth :20
        },
        {
            field : 'description',
            title : '组织描述',
            valign : 'center',
            witth :20
        },
        {
            field : 'orderNum',
            title : '排序',
            align : 'center',
            valign : 'center',
        },
        {
            field : 'delFlag',
            title : '状态',
            align : 'center',
            valign : 'center',
            formatter : function(item, index) {
                if (item.delFlag == '0') {
                    return '<span class="label label-danger">禁用</span>';
                } else if (item.delFlag == '1') {
                    return '<span class="label label-primary">正常</span>';
                }
            }
        },
        {
            title : '操作',
            field : 'organizationId',
            align : 'center',
            valign : 'center',
            formatter : function(item, index) {
                var e = '<a class="btn btn-primary btn-sm " href="#" mce_href="#" title="编辑" onclick="Organ.showEdit(\''
                    + item.id
                    + '\')"><i class="fa fa-edit"></i></a> ';
                var a1 = '<a class="btn btn-success btn-sm" href="#" title="添加同级"  mce_href="#" onclick="Organ.showAdd(\''
                    + item.pid
                    + '\')"><i class="fa fa-plus"></i></a> ';
                var a2 = '<a class="btn btn-primary btn-sm " href="#" title="增加下級"  mce_href="#" onclick="Organ.showAdd(\''
                    + item.id
                    + '\')"><i class="fa fa-plus"></i></a> ';
                var d = '<a class="btn btn-danger btn-sm " href="#" title="删除"  mce_href="#" onclick="Organ.removeOne(\''
                    + item.id
                    + '\')"><i class="fa fa-remove"></i></a> ';
                var enable = '<a class="btn btn-primary btn-sm " href="#" title="启用"  mce_href="#" onclick="Organ.enable(\''
                    + item.id
                    + '\')"><i class="fa fa-unlock"></i></a> ';
                var disable = '<a class="btn btn-warning btn-sm " href="#" title="禁用"  mce_href="#" onclick="Organ.disable(\''
                    + item.id
                    + '\')"><i class="fa fa-lock"></i></a> ';
                return e + a1 + a2 +(item.delFlag==0?enable:disable)+d;
            }
        } ];
    return column;
}

Organ.loadTable = function(){
    var treeTable =  new TreeTable(Organ.organizationTableId,Organ.prefix+"/list","id","id","pid",Organ.initColumn());
    treeTable.init();
}

Organ.showAdd = function(pid){
    layer.open({
        type : 2,
        title : '组织增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : Organ.prefix + '/add/' + pid
    });
}


Organ.showEdit = function(id){
    layer.open({
        type : 2,
        title : '组织修改',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : Organ.prefix + '/edit/' + id
    });
}

Organ.removeOne = function(id){
    layer.confirm("确定删除该组织吗？", {icon: 3, title:'提示'}, function(index){
        //do something
        var ajx = new $ax(Organ.prefix+"/remove/"+id,function (data) {
            if(data.code == 200){
                Alert.ok(data.message);
                Organ.loadTable();
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
Organ.enable = function(id){
    layer.confirm("确定启用该组织吗？", {icon: 3, title:'提示'}, function(index){
        //do something
        var ajx = new $ax(Organ.prefix+"/enableOrDisable/enable/"+id,function (data) {
            if(data.code == 200){
                Alert.ok(data.message);
                Organ.loadTable();
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
Organ.disable = function(id){
    layer.confirm("若该组织存在子组织将会一并禁用，确定禁用该组织吗？", {icon: 3, title:'提示'}, function(index){
        //do something
        var ajx = new $ax(Organ.prefix+"/enableOrDisable/disable/"+id,function (data) {
            if(data.code == 200){
                Alert.ok(data.message);
                Organ.loadTable();
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

