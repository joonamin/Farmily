import { useState, useEffect } from 'react';

export default function ArticleDetail(props) {
  return (
    <>
      <div className="w-full h-4/6 mx-auto mb-4">
        <div className="border-4 p-2 border-black rounded-xl text-left w-full">
          <strong>{props.title}</strong>
        </div>
        <p className="text-right px-10">작성자 : 작성자</p>
        <div className="border-4 p-2 border-black rounded-xl text-left w-full h-60">
          <p>{props.content}</p>
        </div>
      </div>
    </>
  );
}
