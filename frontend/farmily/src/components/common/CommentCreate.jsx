import { useState, useEffect } from 'react';

export default function CommentCreate() {
  return (
    <div>
      <form className="flex items-center rounded-lg pb-4">
        <textarea
          type="text"
          placeholder="댓글을 입력하세요."
          className="border-4 flex-grow py-1 resize-none border-black p-2 rounded-xl"
        ></textarea>
        <button
          type="submit"
          className="bg-stone-300 text-black ml-2 whitespace-pre-line rounded-lg p-5"
        >
          작성
        </button>
      </form>
    </div>
  );
}
