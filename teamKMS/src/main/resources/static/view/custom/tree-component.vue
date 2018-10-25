<template>
<ul class="tree-list">
	<treenode-component :items="items" :busname="busname"></treenode-component>
</ul>
</template>

<script>
module.exports = {
  props: {
    items: {},
    cachekey: "",
    busname : ""
  },
  data: () => ({
    cache: {},
    active: {}
  }),
  created() {
    EventBus.$on(this.busname, this.activated);
    this.recurCache(this.items);
  },
  methods: {
    activated: function activated(node) {      
      this.active.activeNow = false;
      this.active = node;
    },
    addNode: function addNode(data) {
      this.cache[data[this.cachekey]] = data[this.cachekey];
      this.active.items.children.push(data);
      this.active.showChild = true;
    },
    deleteNode: function deleteNode() {      
      delete this.cache[this.active.items[this.cachekey]];
      var index = this.active.parentnode.items.children.indexOf(this.active.items);
      this.active.parentnode.items.children.splice(index, 1);
      this.active = {};
    },
    updateNode: function updateNode(data) {
      this.active.items.name = data;
    },
    moveNode: function moveNode(text) {
      var target = this.cache[text];
      target.children.push(this.active.items);
      var index = this.active.parentnode.items.children.indexOf(this.active.items);
      this.active.parentnode.items.children.splice(index, 1);
    },
    recurCache(data) {
      this.cache[data[this.cachekey]] = data;
      if (data.children && data.children.length) {
        var max = data.children.length;
        for (var i = 0; i < max; i++) {
          this.recurCache(data.children[i]);
        }
      }
    }
  }
};
</script>
  
<style>
.tree-list {
  font-family: Roboto, sans-serif;
  line-height: 1.5;
  padding-left: 28px;
  margin: 8px 0;
  list-style-type: none;
}

.tree-list ul {
  list-style-type: none;
  padding-left: 28px;
}
</style>
