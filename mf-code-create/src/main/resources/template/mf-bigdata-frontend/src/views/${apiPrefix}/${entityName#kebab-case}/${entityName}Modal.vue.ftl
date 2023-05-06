<!--
 @description: ${tableInfo.tableComment}
 @author: cgli
 @date: ${.now?string["yyyy-MM-dd"]}
 @version: V1.0.0
-->
<template>
  <BasicModal v-bind="$attrs" @register="registerModal" :title="getTitle" @ok="handleSubmit">
    <BasicForm @register="registerForm" @submit="handleSubmit" />
  </BasicModal>
</template>
<script lang="ts">
import { ref, computed, unref } from "vue";
import { BasicForm, useForm } from "/@/components/general/Form/index";
import { ${entityName?uncap_first}FormSchema } from "./${entityName?uncap_first}.data";
import { BasicModal, useModalInner } from "/@/components/general/Modal";
import { insert${entityName}, update${entityName} } from "/@/api/${apiPrefix}/${entityName}";

export default {
  name: "${entityName}Modal",
  components: { BasicModal, BasicForm },
  emits: ["success", "register"],
  setup(_, { emit }) {
    const isUpdate = ref(true);
    const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
      labelWidth: 100,
      baseColProps: { span: 12 },
      schemas: ${entityName?uncap_first}FormSchema,
      showActionButtonGroup: false,
      autoSubmitOnEnter: true
    });
    const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
      resetFields().then();
      setModalProps({ confirmLoading: false, width: "800px" });
      isUpdate.value = !!data?.isUpdate;
      if (unref(isUpdate)) {
        setFieldsValue({
          ...data.record
        }).then();
      }
    });
    const getTitle = computed(() => (!unref(isUpdate) ? "新增${tableInfo.tableComment}" : "编辑${tableInfo.tableComment}"));

    async function handleSubmit() {
      let values = await validate();
      setModalProps({ confirmLoading: true });
      if (unref(isUpdate)) {
        save${entityName}(update${entityName}, values);
      } else {
        save${entityName}(insert${entityName}, values);
      }
    }

    function save${entityName}(save, values) {
      save(values).then(() => {
        emit("success");
        closeModal();
      }).finally(() => {
        setModalProps({ confirmLoading: false });
      });
    }

    return {
      registerModal,
      registerForm,
      getTitle,
      handleSubmit
    };
  }
};
</script>
