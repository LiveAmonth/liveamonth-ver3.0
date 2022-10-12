import type { Quill } from "@vueup/vue-quill";

export const useQuillEditor = () => {
  const onEditorReady = (e: Quill, content: string) => {
    e.container.querySelector(".ql-blank").innerHTML = content ? content : "";
  };

  return {
    toolbarOptions: [
      [{ size: ["small", false, "large", "huge"] }], // custom dropdown
      [{ header: [1, 2, 3, false] }],
      ["bold", "italic", "underline", "strike"], // toggled buttons
      [{ list: "ordered" }, { list: "bullet" }],
      [{ color: [] }, { background: [] }], // dropdown with defaults from theme
      [{ align: [] }],
    ],
    onEditorReady,
  };
};
