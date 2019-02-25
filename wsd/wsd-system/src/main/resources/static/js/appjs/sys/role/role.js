var Role = {
	roleTableId:'roleTable',
	prefix:"/sys/role",
    tableobjct: null,
    table:null
}

//初始化表格列
Role.initColumn = function(){
    var columns = [[
        {field: 'selectItem', checkbox: true},
        {title: '角色名称', field: 'name', align: 'center'},
        {title: '角色标题', field: 'title', align: 'center'},
        {title: '角色描述', field: 'description', align: 'center'},
        {title: '创建时间', field: 'ctime', align: 'center'},
        {title: '修改时间', field: 'utime', align: 'center'},
        {title: '操作', field: 'roleId', align: 'center', templet:function(item){
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
            return e + d ;
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
        table.setArea($('#'+Role.userTableId).parent().width(),$('#'+Role.userTableId).parent().height())
        Role.table = table.init(Role.tableobjct);
    });
}

Role.reload = function(){
    var queryData = {};
    // queryData['username'] = $("#searchName").val()==''?null:$("#searchName").val();
    Role.table.refresh(queryData);
}

Role.showAdd=function(){
    layer.open({
        type : 2,
        title : '添加角色',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/add' // iframe的url
    });
}

Role.showEdit=function(id){
        layer.open({
            type : 2,
            title : '角色修改',
            maxmin : true,
            shadeClose : true, // 点击遮罩关闭层
            area : [ '800px', '520px' ],
            content : prefix + '/edit/' + id // iframe的url
        });
}

Role.removeOne=function(id){
    layer.confirm("确定删除该权限吗？", {icon: 3, title:'提示'}, function(index){
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
var prefix = "/sys/role";
$(function() {
    Role.loadTable();
});

function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code === 0) {
					layer.msg("删除成功");
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	})

}
function edit(id) {
	layer.open({
		type : 2,
		title : '角色修改',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function batchRemove() {
	
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	}, function() {
		var ids = new Array();
		$.each(rows, function(i, row) {
			ids[i] = row['roleId'];
		});
		console.log(ids);
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
}