import { useState, useEffect } from 'react';

export default function CommentCreate() {
  return (
    <div>
      <form className="border border-stone-800 p-2 flex items-center rounded-lg">
        <textarea
          type="text"
          placeholder="댓글을 입력하세요."
          className="border-b-2 flex-grow py-1 resize-none border-2 border-stone-700 p-2 rounded-lg"
        ></textarea>
        <button
          type="submit"
          className="bg-stone-300 text-black py-0.5 px-2 ml-2 whitespace-pre-line rounded-lg"
        >
          댓글
          <br />
          작성
        </button>
      </form>
    </div>
  );
}
