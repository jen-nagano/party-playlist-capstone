<template>
  <div class="list-container">
    <ul ref="list">
      <li v-for="item in items" :key="item.id" class="list-item">
        {{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script>
import Sortable from 'sortablejs';

export default {
  data() {
    return {
      items: [
        { id: 1, name: 'Item 1' },
        { id: 2, name: 'Item 2' },
        { id: 3, name: 'Item 3' },
      ],
    };
  },
  mounted() {
    new Sortable(this.$refs.list, {
      animation: 150,
      onUpdate: (evt) => {
        // Update your data model after reordering
        const oldIndex = evt.oldIndex;
        const newIndex = evt.newIndex;
        this.items.splice(newIndex, 0, this.items.splice(oldIndex, 1)[0]); 
      },
    });
  },
};
</script>
