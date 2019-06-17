  <template>

    <div>
        <!-- write-component로 바꿔주세요-->
    <question-component :id="id" :name="name" :qid="qid"></question-component>
    <comment-component :id="Number(id)"
                       :name="name"
                       :qid="Number(qid)"
                       :is-required-code-button="true"></comment-component>
    </div>
  </template>

  <script>
  module.exports = {
    props: ['id', 'name', 'qid']
  }
  </script>
