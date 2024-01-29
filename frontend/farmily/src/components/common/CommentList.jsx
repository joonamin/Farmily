import { useState, useEffect } from 'react';

import CommentItem from './CommentItem.jsx';

const DUMMY_COMMENT = [
  { id: 1, content: '댓글 1', author: { nickname: 'test1' } },
  { id: 2, content: '댓글 2', author: { nickname: 'test2' } },
  { id: 3, content: '댓글 3', author: { nickname: 'test3' } },
  { id: 4, content: '댓글 4', author: { nickname: 'test4' } },
  { id: 5, content: '댓글 5', author: { nickname: 'test5' } },
  { id: 6, content: '댓글 6', author: { nickname: 'test6' } },
  { id: 7, content: '댓글 7', author: { nickname: 'test7' } },
  { id: 8, content: '댓글 8', author: { nickname: 'test8' } },
  { id: 9, content: '댓글 9', author: { nickname: 'test9' } },
  { id: 10, content: '댓글 10', author: { nickname: 'test10' } },
];

export default function CommentList() {
  return (
    <>
      {DUMMY_COMMENT.map((comment) => (
        <CommentItem
          key={comment.id}
          id={comment.id}
          content={comment.content}
          author={comment.author.nickname}
        />
      ))}
    </>
  );
}
