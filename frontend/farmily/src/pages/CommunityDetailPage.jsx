import { useEffect, useState } from 'react';
import axios from '../api/axios.jsx';
import tree from '../assets/images/communityTree.png';
import { useParams } from 'react-router-dom';

export default function CommunityDetailPage() {
  const [community, setCommunity] = useState({
    title: '',
    content: '',
    author: '',
    treeImage: {
      location: '',
      originalFileName: '',
    },
  });

  const id = useParams().communityId;

  useEffect(() => {
    axios
      .get(`/community/${id}`)
      .then((res) => {
        setCommunity(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className="h-full">
      <div className="h-1/6 p-10">
        <div className="border-4 p-2 border-black rounded-xl text-left flex justify-between">
          <p>{community.title}</p>
        </div>
        <p>작성자 : {community.author}</p>
      </div>
      <div className="h-3/6">
        <div className="">
          <img src={tree} alt="" className="mx-auto object-cover" />
        </div>
      </div>
      <div className="h-2/6">{community.content}</div>
    </div>
  );
}
