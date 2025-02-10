import { useEffect, useState } from "react";
import { Tag } from "../../../models/tag/Tag";
import useAuth from "../../../hooks/useAuth";
import { Api, getHeadersAuthorization, getHeadersBodyAuthorization } from "../../../services/Api";

export default function TagFragment() {
  const [listTags, setListTags] = useState<Tag[]>([]);
  const [selectedTag, setSelectedTag] = useState<Tag | null>(null);
  const [newTag, setNewTag] = useState<{ description: string; color: string }>({ description: "", color: "#000000" });
  const [isOpenDeleteModal, setIsOpenDeleteModal] = useState<boolean>(false);
  const { user } = useAuth();

  const fetchTags = async () => {
    try {
      const response = await Api.get<Tag[]>("/tag", getHeadersAuthorization(user?.token));
      setListTags(response.data);
    } catch (error) {
      console.error("Erro ao buscar tags:", error);
    }
  };

  useEffect(() => {
    fetchTags();
  }, [user?.id]);

  const handleAddTag = async () => {
    if (!newTag.description) return;
    try {
      await Api.post("/tag", newTag, getHeadersBodyAuthorization(user?.token));
      setNewTag({ description: "", color: "#000000" });
      fetchTags();
    } catch (error) {
      console.error("Erro ao adicionar tag:", error);
    }
  };

  const handleUpdateTag = async () => {
    if (!user?.token || !selectedTag) return;
    try {
      await Api.put(`/tag`, selectedTag, getHeadersBodyAuthorization(user?.token));
      closeEditModal();
      fetchTags();
    } catch (error) {
      console.error("Erro ao atualizar tag:", error);
    }
  };

  const handleDeleteTag = async () => {
    if (!selectedTag) return;
    try {
      await Api.delete(`/tag/${selectedTag.id}`, getHeadersAuthorization(user?.token));
      setIsOpenDeleteModal(false);
      closeEditModal();
      fetchTags();
    } catch (error) {
      console.error("Erro ao excluir tag:", error);
    }
  };

  const openEditModal = (tag: Tag) => {
    setSelectedTag(tag);
  };

  const closeEditModal = () => {
    setSelectedTag(null);
  };

  return (
    <div className="flex flex-col gap-4">
      <div className="border border-tertiary p-4 rounded-lg shadow-md">
        <h2 className="text-lg font-semibold">Tags</h2>
        <ul className="mt-2 flex flex-col gap-2">
          {listTags.map((tag) => (
            <li
              key={tag.id}
              className="flex items-center gap-2 p-2 rounded-md cursor-pointer bg-gray-100 hover:bg-gray-200 transition-colors"
              onClick={() => openEditModal(tag)}
              style={{ borderLeft: `4px solid ${tag.color}` }}
            >
              <span>{tag.description}</span>
            </li>
          ))}
        </ul>

        {/* Formulário para adicionar nova tag */}
        <form className="mt-4 flex gap-2 items-center" onSubmit={(e) => e.preventDefault()}>
          <input
            type="color"
            value={newTag.color}
            onChange={(e) => setNewTag({ ...newTag, color: e.target.value })}
            className="h-7 w-6"
          />
          <input
            type="text"
            value={newTag.description}
            onChange={(e) => setNewTag({ ...newTag, description: e.target.value })}
            placeholder="Nova tag..."
            className="border py-1 px-2 flex-1 rounded-md"
          />
          <button onClick={handleAddTag} className="bg-blue-500 text-white px-2 py-1 rounded-md">
            Adicionar
          </button>
        </form>
      </div>

      {/* Modal de Edição */}
      {selectedTag && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-black opacity-50"></div>
          <div className="relative bg-white rounded-lg shadow-lg p-4 flex flex-col items-center">
            <h3 className="text-lg font-bold">Editar Tag</h3>
            <input
              type="text"
              value={selectedTag.description}
              onChange={(e) => setSelectedTag({ ...selectedTag, description: e.target.value })}
              className="border p-2 rounded-md w-full mt-2"
            />
            <input
              type="color"
              value={selectedTag.color}
              onChange={(e) => setSelectedTag({ ...selectedTag, color: e.target.value })}
              className="mt-2"
            />
            <div className="flex justify-end mt-4 gap-2">
              <button onClick={closeEditModal} className="px-4 py-2 border rounded-md">Cancelar</button>
              <button onClick={() => setIsOpenDeleteModal(true)} className="px-4 py-2 bg-red-500 text-white rounded-md">Excluir</button>
              <button onClick={handleUpdateTag} className="bg-button-confim text-white px-4 py-2 rounded-md">Salvar</button>
            </div>
          </div>
        </div>
      )}

      {/* Modal de Exclusão */}
      {isOpenDeleteModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-black opacity-50"></div>
          <div className="relative bg-white rounded-lg shadow-lg p-4 flex flex-col items-center">
            <h3 className="text-lg font-bold">Excluir Tag</h3>
            <p className="mt-2 text-sm">Tem certeza que deseja excluir a tag <strong>{selectedTag?.description}</strong>?</p>
            <div className="flex justify-end mt-4 gap-2">
              <button onClick={() => setIsOpenDeleteModal(false)} className="px-4 py-2 border rounded-md">Cancelar</button>
              <button onClick={handleDeleteTag} className="bg-red-500 text-white px-4 py-2 rounded-md">Excluir</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
