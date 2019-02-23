/**
 * layui表格封装工具
 */
(function(){
    var LAYtable = function(tableId,url,cols){
        this.elem = tableId; //指定原始 table 容器的选择器或 DOM
        this.url = url;
        this.cols = cols; //设置表头
        this.ltInstance = null; //layui table对象
        this.height = 400;
        this.title = null;
        this.page = true;
    };

    LAYtable.prototype ={

        init:function(table){
            this.ltInstance = table.render({
                elem: "#"+this.elem//指定原始表格元素选择器（推荐id选择器）
                ,url:this.url
                ,height: this.height //容器高度
                ,cols: this.cols//设置表头
                ,toolbar: "#"+this.elem+'ToolBar' //开启表格头部工具栏区域
                ,defaultToolbar:['filter', 'print', 'exports'] //自由配置头部工具栏右侧的图标
                ,page:this.page//是否开启分页
                ,limit:10//每页显示的条数（默认：10)
                ,limits:[10,20,50,100,200,500,1000]//每页条数的选择项
                ,loading:true //是否显示加载条
                ,title:this.title //定义 table 的大标题
                ,id:this.elem
                //,…… //更多参数参考右侧目录：基本参数选项
            });

            return this;
        },
        setHeight:function(height){ //设置高度
            this.height = height;
        },
        setTitle:function(title){ //设置标题
            this.title = title;
        },
        openPage:function(){ //关闭分页
            this.page = false;
        },
        refresh:function(params){ //刷新表格
            this.ltInstance.reload({
                where: params
                ,page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
        }
    };

    window.LAYtable = LAYtable;
}());