import { useState, useEffect } from 'react';

export default function ArticleDetail(props) {
  return (
    <>
      <div className="w-full h-4/6 mx-auto mb-4">
        <div className="border-4 p-2 border-black rounded-xl text-left w-full">
          <strong>{props.title}</strong>
        </div>
        <div className="flex justify-between">
          <span className="text-left">
            작성일자 : {props.createdAt.slice(0, 10)}
          </span>
          <span className="text-right">작성자 : {props.author.nickname}</span>
        </div>
        <div className="border-4 p-2 border-black rounded-xl text-left w-full h-60">
          <p>{props.content}</p>
        </div>
      </div>
    </>
  );
}
