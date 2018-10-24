<template>
<li class="tree-node">
  <button v-if="items.children && items.children.length" @click="toggle"><i class="material-icons" >{{iconName}}</i></button>
  <span @click="activeChange" v-bind:class="{ active : activeNow }">{{ items.name }}</span>
  <ul v-if="items.children && items.children.length">
    <treenode-component v-if="showChild" v-for="(child,i) in items.children" :key="i" :items="child"></treenode-component>
  </ul>
</li>
</template>

<script>
module.exports = {
  props: {
    items: {}
  },
  data: () => ({
    showChild: false,
    iconName: "arrow_right",
    activeNow : false
  }),
  watch : {

  },
  methods: {
    activeChange: function activeChange() {
      EventBus.$emit("nodechange", this);
      this.activeNow = !this.activeNow;
    },
    toggle: function toggle() {
      this.showChild = !this.showChild;
      if (this.showChild) this.iconName = "arrow_drop_down";
      else this.iconName = "arrow_right";
    }
  }
};
</script>

<style>
.tree-list li {
  cursor: pointer;
}

.active{
  color : blue;
}
</style>
