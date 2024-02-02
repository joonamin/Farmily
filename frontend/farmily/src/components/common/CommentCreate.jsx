import { useState, useEffect } from 'react';
import axios from '../../api/axios.jsx';

export default function CommentCreate({ recordId, onCommentCreate }) {
  const [content, setContent] = useState();
  const handleClick = () => {
    axios
      .post(`record/${recordId}/comment`, {
        content: content,
      })
      .then((response) => {
        onCommentCreate();
        setContent('');
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleChange = (e) => {
    setContent(e.target.value);
  };

  return (
    <div>
      <div className="flex items-center rounded-lg pb-4">
        <textarea
          type="text"
          placeholder="댓글을 입력하세요."
          className="border-4 flex-grow py-1 resize-none border-black p-2 rounded-xl"
          value={content}
          onChange={handleChange}
        ></textarea>
        <button
          className="bg-stone-300 text-black ml-2 whitespace-pre-line rounded-lg p-5"
          onClick={handleClick}
        >
          작성
        </button>
      </div>
    </div>
  );
}
