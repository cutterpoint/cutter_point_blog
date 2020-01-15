<template>
</template>
<script>
export default {
  name: 'LoadJs',
  props: {
        src:{
            type:String,
            required:true
        }
  },
  render: function() {
      var _self = this;
      return createElement('script', {
          attrs: {
              type: 'text/javascript',
              src: this.src
          },
          on: {
              load: function(event) {
                  _self.$emit('load', event);
              },
              error: function(event) {
                  _self.$emit('error', event);
              },
              readystatechange: function(event) {
                if(this.readyState == 'complete') {
                    _self.$emit('load', event);
                }
              }
              
          }
      });
  }
}
</script>
