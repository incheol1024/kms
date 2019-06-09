  <template>

    <div>
        <!-- write-component로 바꿔주세요-->
    <question-component :id="id" :name="name" :qid="qid"></question-component>
    <comment-component :id="id" :name="name" :qid="qid"></comment-component>
    </div>
  </template>

  <script>
  module.exports = {
    props: ['id', 'name', 'qid']
  }
  </script>
