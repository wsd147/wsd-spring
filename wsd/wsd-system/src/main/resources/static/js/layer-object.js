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
}