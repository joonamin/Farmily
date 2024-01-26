import { useState, useEffect } from 'react';

export default function CommentCreate() {
  return (
    <div>
      <form className="border border-stone-800 p-2">
        <input
          type="text"
          placeholder="댓글을 입력하세요."
          className="border-b-2 w-full py-1"
        />
        <button
          type="submit"
          className="bg-blue-500 text-white py-1 px-4 mt-2 rounded"
        >
          댓글 작성
        </button>
      </form>
    </div>
  );
}
