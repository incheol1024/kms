<template>
<li class="tree-node">
  <button v-if="items.children && items.children.length" @click="toggle"><i class="material-icons" >{{iconName}}</i></button>
  <span @click="activeChange" v-bind:class="{ active : activeNow }">{{ items.name }}</span>
  <ul v-if="items.children && items.children.length">
    <treenode-component v-if="showChild" v-for="(child,i) in items.children" :key="i" :items="child" :parentnode="getParent()"></treenode-component>
  </ul>
</li>
</template>

<script>
module.exports = {
  props: {
    items: {},
    parentnode: {}
  },
  data: () => ({
    showChild: false,
    iconName: "keyboard_arrow_right",
    activeNow: false
  }),
  methods: {
    activeChange: function activeChange() {
      EventBus.$emit("nodechange", this);
      this.activeNow = !this.activeNow;
    },
    toggle: function toggle() {
      this.showChild = !this.showChild;
      if (this.showChild) this.iconName = "keyboard_arrow_down";
      else this.iconName = "keyboard_arrow_right";
    },
    getParent: function getParent() {
      return this;
    }
  }
};
</script>

<style>
.tree-node{
  align-items: center;
  cursor: pointer;
}

.tree-node i {
  color: rgba(0, 0, 0, 0.54);
}

.tree-node span {
  font-size: 1.2rem;
  color: rgba(0, 0, 0, 0.87);
  vertical-align: top;
}

.active {
  color: #1976d2 !important;
}
</style>
