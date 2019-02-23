var Organ ={
    organizationTableId:"organizationTableId",
    prefix:"/sys/organization"
}

Organ.initColumn= function(){
    var column = [
        {
            title : '编号',
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
                    + item.organizationId
                    + '\')"><i class="fa fa-edit"></i></a> ';
                var a1 = '<a class="btn btn-success btn-sm" href="#" title="添加同级"  mce_href="#" onclick="Organ.showAdd(\''
                    + item.pid
                    + '\')"><i class="fa fa-plus"></i></a> ';
                var a2 = '<a class="btn btn-primary btn-sm " href="#" title="增加下級"  mce_href="#" onclick="Organ.showAdd(\''
                    + item.organizationId
                    + '\')"><i class="fa fa-plus"></i></a> ';
                var d = '<a class="btn btn-warning btn-sm " href="#" title="删除"  mce_href="#" onclick="removeone(\''
                    + item.organizationId
                    + '\')"><i class="fa fa-remove"></i></a> ';
                return e + a1 + a2 +d;
            }
        } ];
    return column;
}

Organ.loadTable = function(){
    var treeTable =  new TreeTable(Organ.organizationTableId,Organ.prefix+"/list","organizationId","organizationId","pid",Organ.initColumn());
    treeTable.init();
}

Organ.showAdd = function(pid){
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : Organ.prefix + '/add/' + pid
    });
}


Organ.showEdit = function(organizationId){
    Alert.ok(123);
}

Organ.save = function(){
    var ajx = new $ax(Organ.prefix+"/save",function (data) {
        if(data.code == 200){
            Alert.ok(data.message);
            layer.close();
        }else {
            Alert.error(data.message);
        }
    },function () {
        Alert.error("网络错误")
    });
    ajx.setData($('#signupForm').serialize());
    ajx.start();
}

