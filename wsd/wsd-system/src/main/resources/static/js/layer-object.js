var Alert ={

    msg:function(content,icon){
      layer.msg(content,{icon:icon});
    },
    warn:function(content){
        Alert.msg(content,0);
    },
    ok:function(content){
        Alert.msg(content,1);
    },
    error:function(content){
        Alert.msg(content,2);
    },
    fail:function(content){
        Alert.msg(content,5);
    },
    success:function(content){
        Alert.msg(content,6);
    },
    comfirm:function(content,action){
        layer.confirm(content, {icon: 3, title:'提示'}, function(index){
            //do something
            action
            layer.close(index);
        });
    }
}