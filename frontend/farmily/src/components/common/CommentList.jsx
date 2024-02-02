import { useState, useEffect } from 'react';

import CommentItem from './CommentItem.jsx';

export default function CommentList({ comments }) {
  return (
    <>
      {comments.map((comment) => (
        <CommentItem key={comment.id} {...comment} />
      ))}
    </>
  );
}
