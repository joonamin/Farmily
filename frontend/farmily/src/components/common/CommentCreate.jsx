import { useState, useEffect } from 'react';
import axios from '../../api/axios.jsx';

export default function CommentCreate({ recordId, onCommentCreate }) {
  const [content, setContent] = useState();
  const [error, setError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleClick = () => {
    if (!content) {
      setError(true);
      setErrorMessage('내용을 입력해 주세요.');
      return;
    }

    axios
      .post(`record/${recordId}/comment`, {
        content: content,
      })
      .then((response) => {
        onCommentCreate();
        setContent('');
        setError(false);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleChange = (e) => {
    setContent(e.target.value.trim());
  };

  return (
    <div>
      <div className="flex items-center rounded-lg pb-4">
        <textarea
          type="text"
          placeholder={error ? errorMessage : '댓글을 입력하세요.'}
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
