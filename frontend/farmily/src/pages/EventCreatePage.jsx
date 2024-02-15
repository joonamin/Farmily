import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import axios from '../api/axios.jsx';

import SmallButton from '../components/button/SmallButton.jsx';

export default function EventCreatePage() {
  const [title, setTitle] = useState('');
  const [images, setImages] = useState([]);
  const [imageDescriptions, setImageDescriptions] = useState([]);
  const [titleError, setTitleError] = useState(''); // 새로운 상태 추가

  const navigate = useNavigate();
  const family = useSelector((state) => state.family.value);

  const handleImageChange = (event) => {
    const files = event.target.files;
    if (files) {
      const newImages = Array.from(files).map((file) => ({
        file: file,
        url: URL.createObjectURL(file),
        description: '',
      }));
      setImages((prevImages) => [...prevImages, ...newImages]);
      setImageDescriptions((prevDescriptions) => [
        ...prevDescriptions,
        ...newImages.map(() => ''),
      ]);
    }
  };

  const handleImageDescriptionChange = (index, description) => {
    setImageDescriptions((prevDescriptions) => {
      const updatedDescriptions = [...prevDescriptions];
      updatedDescriptions[index] = description;
      return updatedDescriptions;
    });
  };

  // 제목 입력값이 변경될 때마다 실행되는 함수
  const handleTitleChange = (event) => {
    const newTitle = event.target.value;
    setTitle(newTitle);

    // 제목이 비어있는지 확인하고 상태 갱신
    setTitleError(newTitle.trim() === '' ? '제목을 입력하세요.' : '');
  };

  // 글쓰기 버튼 클릭 시 실행되는 함수
  const handleCreateEvent = () => {
    // 제목이 비어있다면 경고 표시
    if (title.trim() === '') {
      setTitleError('제목을 입력하세요.');
      return; // 함수 종료
    }

    if (images.length === 0) {
      setTitleError('이미지를 추가해 주세요.');
      return;
    }

    const formData = new FormData();
    formData.append('sprintId', family.mainSprint.sprintId);
    formData.append('familyId', family.id);
    formData.append('title', title);

    images.forEach((image, index) => {
      formData.append(`imageCards[${index}].imageFile`, image.file);
      formData.append(
        `imageCards[${index}].description`,
        imageDescriptions[index]
      );
    });

    axios
      .post('/record/event', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      })
      .then((response) => {
        navigate(`/family/record/${family.mainSprint.sprintId}`);
      })
      .catch((error) => {
        console.error('Error creating event:', error);
      });
  };

  return (
    <div className="flex flex-col items-center h-full">
      <h1 className="text-2xl font-bold mb-2">이벤트 글쓰기</h1>
      <form className="w-10/12 h-5/6 p-1 rounded-lg">
        {/* 제목 입력란 위에 경고 표시 */}
        <input
          type="text"
          placeholder="제목"
          className="border-4 border-black rounded-lg p-2 w-full"
          value={title}
          onChange={handleTitleChange}
        />
        <div className="w-full flex justify-start pl-4 mb-2">
          <input
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            className="mt-2"
            multiple
          />
        </div>
        <div className="flex flex-no-wrap gap-4 overflow-x-auto h-3/4">
          {images.map((image, index) => (
            <div
              key={index}
              className="relative border border-gray-200 flex-shrink-0 snap-center bg-white shadow-md rounded-lg max-w-sm dark:bg-gray-800 dark:border-gray-700 p-3"
            >
              <img
                src={image.url}
                alt={`Selected ${index + 1}`}
                className="object-contain w-48 h-48 mb-2 rounded"
              />
              <textarea
                type="text"
                placeholder="최대 40자까지 입력 가능합니다."
                value={imageDescriptions[index]}
                maxLength={40}
                onChange={(e) =>
                  handleImageDescriptionChange(index, e.target.value)
                }
                className="border border-stone-200 rounded p-2 w-full absolute bottom-0 left-0 bg-white h-1/3 overflow-hidden whitespace-normal resize-none"
              ></textarea>
            </div>
          ))}
        </div>
      </form>
      <div onClick={handleCreateEvent}>
        <SmallButton text="글쓰기" />
      </div>
      <p className="text-red-400">{titleError}</p>
    </div>
  );
}
